import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioPage } from './portfolio.component';

const meta: Meta<PortfolioPage> = {
  title: 'Components/PortfolioPage',
  component: PortfolioPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioPage>;

export const Primary: Story = {
  args: {},
};
