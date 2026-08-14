import type { Meta, StoryObj } from '@storybook/angular';
import { AuthCard } from './auth-card.component';

const meta: Meta<AuthCard> = {
  title: 'Components/AuthCard',
  component: AuthCard,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AuthCard>;

export const Primary: Story = {
  args: {},
};
