import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioTopbar } from './portfolio-topbar.component';

const meta: Meta<PortfolioTopbar> = {
  title: 'Components/PortfolioTopbar',
  component: PortfolioTopbar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioTopbar>;

export const Primary: Story = {
  args: {},
};
