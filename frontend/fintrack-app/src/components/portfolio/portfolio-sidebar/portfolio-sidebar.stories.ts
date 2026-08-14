import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioSidebar } from './portfolio-sidebar.component';

const meta: Meta<PortfolioSidebar> = {
  title: 'Components/PortfolioSidebar',
  component: PortfolioSidebar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioSidebar>;

export const Primary: Story = {
  args: {},
};
